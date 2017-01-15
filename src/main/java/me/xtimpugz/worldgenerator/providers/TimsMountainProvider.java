package me.xtimpugz.worldgenerator.providers;

import org.terasology.entitySystem.Component;
import org.terasology.math.TeraMath;
import org.terasology.math.geom.BaseVector2i;
import org.terasology.math.geom.Rect2i;
import org.terasology.math.geom.Vector2f;
import org.terasology.rendering.nui.properties.Range;
import org.terasology.utilities.procedural.BrownianNoise;
import org.terasology.utilities.procedural.PerlinNoise;
import org.terasology.utilities.procedural.SubSampledNoise;
import org.terasology.world.generation.ConfigurableFacetProvider;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Updates;
import org.terasology.world.generation.facets.SurfaceHeightFacet;

@Updates(@Facet(SurfaceHeightFacet.class))
public class TimsMountainProvider implements ConfigurableFacetProvider {
    private SubSampledNoise mountainNoise;
    private TimsMountainConfiguration config = new TimsMountainConfiguration();

    @Override
    public void setSeed(long seed) {
        long worldSeed = seed;
        mountainNoise = new SubSampledNoise(new BrownianNoise(new PerlinNoise(worldSeed + 2), 8), new Vector2f(0.001f, 0.001f), 1);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void process(GeneratingRegion region) {
        SurfaceHeightFacet facet = region.getRegionFacet(SurfaceHeightFacet.class);
        float mountainHeight = config.mountainHeight;
        Rect2i processRegion = facet.getWorldRegion();
        for (BaseVector2i position : processRegion.contents()) {
            float additiveMountainHeight = mountainNoise.noise(position.x(), position.y()) * mountainHeight;
            additiveMountainHeight = TeraMath.clamp(additiveMountainHeight, 0, mountainHeight);
            facet.setWorld(position, facet.getWorld(position) + additiveMountainHeight);
        }
    }

    @Override
    public String getConfigurationName() {
        return "TimsMountains";
    }

    @Override
    public Component getConfiguration() {
        return config;
    }

    @Override
    public void setConfiguration(Component configuration) {
        if (configuration instanceof TimsMountainConfiguration)
            config = (TimsMountainConfiguration) configuration;
    }

    private static class TimsMountainConfiguration implements Component {
        @Range(min = 200, max = 500f, increment = 20f, precision = 1, description = "Mountain Height")
        private float mountainHeight = 400;
    }
}

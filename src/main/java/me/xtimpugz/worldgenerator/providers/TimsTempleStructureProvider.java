package me.xtimpugz.worldgenerator.providers;

import me.xtimpugz.worldgenerator.RandomUtils;
import me.xtimpugz.worldgenerator.facets.TempleFacet;
import me.xtimpugz.worldgenerator.structures.Temple;
import org.slf4j.LoggerFactory;
import org.terasology.math.Region3i;
import org.terasology.math.geom.BaseVector2i;
import org.terasology.utilities.procedural.WhiteNoise;
import org.terasology.world.generation.*;
import org.terasology.world.generation.facets.SurfaceHeightFacet;

/**
 * Created by Tim Verhaegen on 3/12/2016.
 */
@Requires(@Facet(value = SurfaceHeightFacet.class, border = @FacetBorder(sides = 28, bottom = 28, top = 28)))
@Produces(TempleFacet.class)
public class TimsTempleStructureProvider implements FacetProvider {
    private WhiteNoise noise;

    @Override
    public void process(GeneratingRegion region) {

        Border3D border = region.getBorderForFacet(TempleFacet.class).extendBy(28, 28, 28);
        TempleFacet templeFacet = new TempleFacet(region.getRegion(), border);
        SurfaceHeightFacet facet = region.getRegionFacet(SurfaceHeightFacet.class);
        Region3i worldRegion = templeFacet.getWorldRegion();

        for (BaseVector2i pos : facet.getWorldRegion().contents()) {
            if (region.getRegion().encompasses(pos.getX(), (int) facet.getWorld(pos), pos.getY())) {
                if (noise.noise(pos.x(), pos.y()) > 0.99) {
                    if (RandomUtils.shouldPlace(Temple.getSpawnChance())) {
                        LoggerFactory.getLogger(TimsTempleStructureProvider.class).info("after random");
                        templeFacet.setWorld(pos.x(), (int) facet.getWorld(pos), pos.getY(), new Temple());
                    }
            }
            }
        }

        region.setRegionFacet(TempleFacet.class, templeFacet);
    }
    @Override public void setSeed(long seed) {
        noise = new WhiteNoise(seed);
    }
}

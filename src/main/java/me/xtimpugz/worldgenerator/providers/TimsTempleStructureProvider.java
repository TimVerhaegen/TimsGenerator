package me.xtimpugz.worldgenerator.providers;

import me.xtimpugz.worldgenerator.facets.TempleFacet;
import me.xtimpugz.worldgenerator.structures.Temple;
import org.terasology.math.geom.BaseVector2i;
import org.terasology.utilities.procedural.WhiteNoise;
import org.terasology.world.generation.*;
import org.terasology.world.generation.facets.SurfaceHeightFacet;

/**
 * Created by Tim Verhaegen on 3/12/2016.
 */
@Requires(@Facet(value = SurfaceHeightFacet.class, border = @FacetBorder(sides = 20, bottom = 20, top = 20)))
@Produces(TempleFacet.class)
public class TimsTempleStructureProvider implements FacetProvider {
    private WhiteNoise noise;

    @Override
    public void process(GeneratingRegion region) {

        Border3D border = region.getBorderForFacet(TempleFacet.class);
        TempleFacet facet = new TempleFacet(region.getRegion(), border);
        SurfaceHeightFacet surfaceHeightFacet = region.getRegionFacet(SurfaceHeightFacet.class);

        for (BaseVector2i position : surfaceHeightFacet.getWorldRegion().contents()) {
            int surfaceHeight = (int) surfaceHeightFacet.getWorld(position);

            if (facet.getWorldRegion().encompasses(position.getX(), surfaceHeight, position.getY())
                && noise.noise(position.getX(), position.getY()) >= 0.99) {
                facet.setWorld(position.getX(), surfaceHeight, position.getY(), new Temple());
            }
        }

        region.setRegionFacet(TempleFacet.class, facet);
    }

    @Override public void setSeed(long seed) {
        noise = new WhiteNoise(seed);
    }
}

package me.xtimpugz.worldgenerator.providers;

import me.xtimpugz.worldgenerator.RandomUtils;
import me.xtimpugz.worldgenerator.facets.TempleFacet;
import org.terasology.math.TeraMath;
import org.terasology.math.geom.Rect2i;
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

        Border3D border = region.getBorderForFacet(TempleFacet.class).extendBy(60, 60, 60);

        TempleFacet templeFacet = new TempleFacet(region.getRegion(), border);
        SurfaceHeightFacet facet = region.getRegionFacet(SurfaceHeightFacet.class);
        Rect2i worldRegion = facet.getWorldRegion();
        for (int wz = worldRegion.minY(); wz <= worldRegion.maxY(); wz++) {
            for (int wx = worldRegion.minX(); wx <= worldRegion.maxX(); wx++) {
                int surfaceHeight = TeraMath.floorToInt(facet.getWorld(wx, wz));

                // check if height is within this region
                if (surfaceHeight >= templeFacet.getWorldRegion().minY() &&
                    surfaceHeight <= templeFacet.getWorldRegion().maxY()) {

                    if (noise.noise(wx, wz) > 0.99 && RandomUtils.shouldPlace(4)) {
                        templeFacet.setWorld(wx, surfaceHeight, wz, true);

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

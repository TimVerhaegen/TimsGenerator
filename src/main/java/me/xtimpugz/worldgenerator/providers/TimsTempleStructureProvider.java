package me.xtimpugz.worldgenerator.providers;

import me.xtimpugz.worldgenerator.RandomUtils;
import me.xtimpugz.worldgenerator.facets.TempleFacet;
import me.xtimpugz.worldgenerator.structures.Temple;
import org.terasology.math.TeraMath;
import org.terasology.math.geom.Rect2i;
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

        Border3D border = region.getBorderForFacet(TempleFacet.class).extendBy(20, 20, 20);
        TempleFacet facet = new TempleFacet(region.getRegion(), border);
        SurfaceHeightFacet surfaceHeightFacet = region.getRegionFacet(SurfaceHeightFacet.class);

        Rect2i worldRegion = surfaceHeightFacet.getWorldRegion();

        for (int wz = worldRegion.minY(); wz <= worldRegion.maxY(); wz++) {
            for (int wx = worldRegion.minX(); wx <= worldRegion.maxX(); wx++) {
                int surfaceHeight = TeraMath.floorToInt(surfaceHeightFacet.getWorld(wx, wz));
                if (surfaceHeight >= facet.getWorldRegion().minY() &&
                    surfaceHeight <= facet.getWorldRegion().maxY()) {

                    if (noise.noise(wx, wz) > 0.99) {
                        if (RandomUtils.shouldPlace(Temple.getSpawnChance())) {
                            facet.setWorld(wx, surfaceHeight, wz, new Temple());
                        }
                    }
                }
            }
        }

        region.setRegionFacet(TempleFacet.class, facet);
    }

    @Override public void setSeed(long seed) {
        noise = new WhiteNoise(seed);
    }
}

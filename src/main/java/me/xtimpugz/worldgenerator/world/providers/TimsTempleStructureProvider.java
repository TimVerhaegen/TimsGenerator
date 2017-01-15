package me.xtimpugz.worldgenerator.world.providers;

import me.xtimpugz.worldgenerator.world.facets.TempleFacet;
import me.xtimpugz.worldgenerator.world.structures.Temple;
import org.terasology.math.TeraMath;
import org.terasology.math.geom.Rect2i;
import org.terasology.utilities.procedural.WhiteNoise;
import org.terasology.world.generation.*;
import org.terasology.world.generation.facets.SurfaceHeightFacet;

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
                if (surfaceHeight >= templeFacet.getWorldRegion().minY() &&
                    surfaceHeight <= templeFacet.getWorldRegion().maxY()) {

                    if (noise.noise(wx, wz) > 0.9999) {
                        templeFacet.setWorld(wx, surfaceHeight, wz, new Temple());
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

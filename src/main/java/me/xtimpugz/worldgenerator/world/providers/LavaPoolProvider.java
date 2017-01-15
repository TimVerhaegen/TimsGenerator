package me.xtimpugz.worldgenerator.world.providers;

import me.xtimpugz.worldgenerator.world.facets.LavaPoolFacet;
import me.xtimpugz.worldgenerator.world.facets.UndergroundSurfaceFacet;
import me.xtimpugz.worldgenerator.world.structures.LavaPool;
import org.terasology.math.TeraMath;
import org.terasology.math.geom.Rect2i;
import org.terasology.utilities.procedural.WhiteNoise;
import org.terasology.world.generation.*;

/**
 * Created by jove on 14/01/2017.
 */
@Requires(@Facet(value = UndergroundSurfaceFacet.class, border = @FacetBorder(sides = 0, bottom = 50, top = 0)))
@Produces(LavaPoolFacet.class)
public class LavaPoolProvider implements FacetProvider{
    private WhiteNoise noise;

    @Override
    public void process(GeneratingRegion region) {

        Border3D border = region.getBorderForFacet(LavaPoolFacet.class).extendBy(1, 60, 1);
        LavaPoolFacet lavaPoolFacet = new LavaPoolFacet(region.getRegion(), border);
        UndergroundSurfaceFacet facet = region.getRegionFacet(UndergroundSurfaceFacet.class);
        Rect2i worldRegion = facet.getWorldRegion();

        for (int wz = worldRegion.minY(); wz <= worldRegion.maxY(); wz++) {
            for (int wx = worldRegion.minX(); wx <= worldRegion.maxX(); wx++) {
                int surfaceHeight = TeraMath.floorToInt(facet.getWorld(wx, wz));
                if (surfaceHeight >= lavaPoolFacet.getWorldRegion().minY() &&
                        surfaceHeight <= lavaPoolFacet.getWorldRegion().maxY()) {
                    if (noise.noise(wx, wz) > 0.999) {
                        if(region.getRegion().encompasses(wx, surfaceHeight + 48, wz))
                            lavaPoolFacet.setWorld(wx, surfaceHeight+48, wz,new LavaPool());
                    }
                }
            }
        }
        region.setRegionFacet(LavaPoolFacet.class, lavaPoolFacet);
    }

    @Override public void setSeed(long seed) {
        noise = new WhiteNoise(seed);
    }
}


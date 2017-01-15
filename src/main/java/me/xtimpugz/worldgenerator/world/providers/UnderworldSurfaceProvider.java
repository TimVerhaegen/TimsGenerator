package me.xtimpugz.worldgenerator.world.providers;

import me.xtimpugz.worldgenerator.world.facets.UndergroundSurfaceFacet;
import org.terasology.math.geom.*;
import org.terasology.utilities.procedural.Noise3DTo2DAdapter;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.utilities.procedural.SubSampledNoise2D;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Produces;

import java.util.HashMap;

@Produces(UndergroundSurfaceFacet.class)
public class UnderworldSurfaceProvider implements FacetProvider {

    private long seed;
    public static HashMap<Vector3i, UndergroundSurfaceFacet> facetHashMap;

    @Override
    public void setSeed(long seedP) {
        seed = seedP;
    }

    SubSampledNoise2D noise;
    @Override
    public void initialize() {
        facetHashMap = new HashMap<Vector3i, UndergroundSurfaceFacet>();

        noise = new SubSampledNoise2D(new Noise3DTo2DAdapter(new SimplexNoise(seed), 0), new Vector2f(0.01f, 0.01f), 1);
    }

    @Override
    public void process(GeneratingRegion region) {
        Border3D border = region.getBorderForFacet(UndergroundSurfaceFacet.class);
        UndergroundSurfaceFacet facet  = new UndergroundSurfaceFacet(region.getRegion(), border);
        Rect2i rect2D = facet.getWorldRegion();
        for(BaseVector2i vect : rect2D.contents()) {
            int x = vect.x();
            int z = vect.y();
            int y = Math.round(noise.noise(x, z) * 20) - 500000;
            facet.setWorld(x, z, y);
        }
        facetHashMap.put(new Vector3i(region.getRegion().minX(), region.getRegion().minY(), region.getRegion().minZ()), facet);
        region.setRegionFacet(UndergroundSurfaceFacet.class, facet);
    }
}
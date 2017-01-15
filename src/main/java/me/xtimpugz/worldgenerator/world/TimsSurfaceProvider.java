package me.xtimpugz.worldgenerator.world;

import org.terasology.math.geom.*;
import org.terasology.utilities.procedural.Noise3DTo2DAdapter;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.utilities.procedural.SubSampledNoise2D;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Produces;
import org.terasology.world.generation.facets.SurfaceHeightFacet;

import java.util.HashMap;

@Produces(SurfaceHeightFacet.class)
public class TimsSurfaceProvider implements FacetProvider {

    private long worldSeed;
    private SubSampledNoise2D surfaceNoise;
    public static HashMap<Vector3i, SurfaceHeightFacet> facetHashMap;

    @Override
    public void setSeed(long seed) {
        surfaceNoise = new SubSampledNoise2D(new Noise3DTo2DAdapter(new SimplexNoise(seed), 0), new Vector2f(0.01f, 0.01f), 1);
        worldSeed = seed;
    }

    @Override
    public void initialize() {
        facetHashMap = new HashMap<Vector3i, SurfaceHeightFacet>();

    }

    @Override
    public void process(GeneratingRegion region) {
        Border3D border = region.getBorderForFacet(SurfaceHeightFacet.class);
        SurfaceHeightFacet facet = new SurfaceHeightFacet(region.getRegion(), border);

        Rect2i processRegion = facet.getWorldRegion();
        for (BaseVector2i position : processRegion.contents()) {
            int x = position.x();
            int z = position.y();
            int y = Math.round(surfaceNoise.noise(x,z) * 20);
            facet.setWorld(position, y);
        }
        facetHashMap.put(new Vector3i(region.getRegion().minX(), region.getRegion().minY(), region.getRegion().minZ()), facet);
        region.setRegionFacet(SurfaceHeightFacet.class, facet);
    }
}

package me.xtimpugz.worldgenerator;

import org.terasology.math.geom.BaseVector2i;
import org.terasology.math.geom.Rect2i;
import org.terasology.math.geom.Vector2f;
import org.terasology.utilities.procedural.Noise3DTo2DAdapter;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.utilities.procedural.SubSampledNoise2D;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Produces;
import org.terasology.world.generation.facets.SurfaceHeightFacet;

@Produces(SurfaceHeightFacet.class)
public class TimsSurfaceProvider implements FacetProvider {

        private long worldSeed;
        private SubSampledNoise2D surfaceNoise;

        @Override
        public void setSeed(long seed) {
                surfaceNoise = new SubSampledNoise2D(new Noise3DTo2DAdapter(new SimplexNoise(seed), 0), new Vector2f(0.01f, 0.01f), 1);
                worldSeed = seed;
        }

        @Override
        public void process(GeneratingRegion region) {
                Border3D border = region.getBorderForFacet(SurfaceHeightFacet.class);
                SurfaceHeightFacet facet = new SurfaceHeightFacet(region.getRegion(), border);

                Rect2i processRegion = facet.getWorldRegion();
                for (BaseVector2i position : processRegion.contents()) {
                        facet.setWorld(position, surfaceNoise.noise(position.x(), position.y()) * 20);

                }

                region.setRegionFacet(SurfaceHeightFacet.class, facet);
        }
}

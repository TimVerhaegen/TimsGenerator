package me.xtimpugz.worldgenerator.structures;

import me.xtimpugz.worldgenerator.TimsWorldGenerator;
import me.xtimpugz.worldgenerator.facets.TempleFacet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.math.geom.ImmutableVector3i;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.ChunkConstants;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizer;
import org.terasology.world.generation.facets.SurfaceHeightFacet;

import java.util.Map.Entry;

/**
 * Created by Tim Verhaegen on 3/12/2016.
 */
public class TempleRasterizer implements WorldRasterizer {
    private Block stone;
    private Block dirt;
    private Block water;
    private Block air;
    private Logger logger = LoggerFactory.getLogger(TimsWorldGenerator.class);

    public static int getSize() {
        return 28;
    }

    @Override
    public void initialize() {
        stone = CoreRegistry.get(BlockManager.class).getBlock("Core:Stone");
        dirt = CoreRegistry.get(BlockManager.class).getBlock("Core:Dirt");
        water = CoreRegistry.get(BlockManager.class).getBlock("Core:Water");
        air = CoreRegistry.get(BlockManager.class).getBlock("engine:air");

    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        TempleFacet templeFacet = chunkRegion.getFacet(TempleFacet.class);
        
        SurfaceHeightFacet surfaceHeightFacet = chunkRegion.getFacet(SurfaceHeightFacet.class);
        for (Entry<Vector3i, Boolean> entry : templeFacet.getWorldEntries().entrySet()) {
            Vector3i vector = entry.getKey();
            if (entry.getValue()) {
                if (templeFacet.getWorld(vector.x(), vector.getY(), vector.getZ())) {
                    Vector3i basePosition = new Vector3i(vector.x(), vector.getY(), vector.getZ());
                    int size = TempleRasterizer.getSize();
                    int min = 0;
                    int height = (TempleRasterizer.getSize() + 1) / 2;

                    // Vector3i under = new Vector3i(v).add((size / 2 - 1), 0, 0);
                    //Vector3i top = new Vector3i(v).add((size / 2 + 1), 2, size);

                    // Vector3i under2 = new Vector3i(v).add(0, 0, (size / 2 - 1));
                    //Vector3i top2 = new Vector3i(v).add(size, 2, (size / 2 + 1));

                    for (int i = 0; i <= height; i++) {
                        generateLayer(size, min, i, new ImmutableVector3i(basePosition), chunk);
                        min++;
                        size--;
                    }

                }
            }
        }

    }

    private void generateLayer(int size, int min, int y, ImmutableVector3i centerVector, CoreChunk chunk) {
        for (int x = min; x <= size; x++) {
            for (int z = min; z <= size; z++) {

                Vector3i chunkBlockPosition = new Vector3i(x, y, z).add(centerVector);
                logger.info(chunkBlockPosition.toString());
                if (ChunkConstants.CHUNK_REGION.encompasses(chunk.chunkToWorldPosition(chunkBlockPosition))) {

                    chunk.setBlock(chunk.chunkToWorldPosition(chunkBlockPosition), stone);
                }

            }
        }
    }

}

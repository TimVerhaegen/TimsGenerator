package me.xtimpugz.worldgenerator.structures;

import me.xtimpugz.worldgenerator.facets.TempleFacet;
import org.terasology.math.ChunkMath;
import org.terasology.math.Region3i;
import org.terasology.math.geom.BaseVector3i;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizer;

import java.util.Map.Entry;

public class TempleRasterizer implements WorldRasterizer {
    private Block stone;
    private Block dirt;

    public static int getSize() {
        return 22;
    }

    @Override
    public void initialize() {
        stone = CoreRegistry.get(BlockManager.class).getBlock("Core:Stone");
        dirt = CoreRegistry.get(BlockManager.class).getBlock("Core:Dirt");
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        TempleFacet templeFacet = chunkRegion.getFacet(TempleFacet.class);

        for (Entry<BaseVector3i, Temple> entry : templeFacet.getWorldEntries().entrySet()) {
            Vector3i basePosition = new Vector3i(entry.getKey());
            int size = TempleRasterizer.getSize();
            int min = 0;
            int height = (TempleRasterizer.getSize() + 1) / 2;

            Vector3i under = new Vector3i(basePosition).add((size / 2 - 1), 1, 0);
            Vector3i top = new Vector3i(basePosition).add((size / 2 + 1), 3, size);

            Vector3i under2 = new Vector3i(basePosition).add(0, 1, (size / 2 - 1));
            Vector3i top2 = new Vector3i(basePosition).add(size, 3, (size / 2 + 1));

            Region3i region3i1 = Region3i.createFromMinMax(under, top);
            Region3i region3i2 = Region3i.createFromMinMax(under2, top2);

            for (int i = 1; i < 50; i++) {
                for (int x = 0; x <= size; x++) {
                    for (int z = 0; z <= size; z++) {
                        Vector3i chunkBlockPosition = new Vector3i(x, 0, z).add(basePosition).sub(0, i, 0);
                        if (chunk.getRegion().encompasses(chunkBlockPosition))
                            chunk.setBlock(ChunkMath.calcBlockPos(chunkBlockPosition), dirt);

                    }
                }
            }
            for (int i = 0; i <= height; i++) {
                for (int x = min; x <= size; x++) {
                    for (int z = min; z <= size; z++) {
                        Vector3i chunkBlockPosition = new Vector3i(x, i, z).add(basePosition);
                        if (chunk.getRegion().encompasses(chunkBlockPosition) && !region3i1.encompasses(chunkBlockPosition) && !region3i2.encompasses(chunkBlockPosition))
                            chunk.setBlock(ChunkMath.calcBlockPos(chunkBlockPosition), stone);

                    }
                }
                min++;
                size--;
            }

        }
    }
}

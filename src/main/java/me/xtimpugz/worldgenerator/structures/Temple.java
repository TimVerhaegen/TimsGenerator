package me.xtimpugz.worldgenerator.structures;

import me.xtimpugz.worldgenerator.ISpawnableStructure;
import me.xtimpugz.worldgenerator.TimsWorldGenerator;
import me.xtimpugz.worldgenerator.facets.TempleFacet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.math.ChunkMath;
import org.terasology.math.geom.BaseVector3i;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;

import java.util.Map.Entry;

/**
 * Created by Tim Verhaegen on 3/12/2016.
 */
public class Temple implements ISpawnableStructure {
    private Block stone;
    private Block dirt;
    private Block water;
    private Block air;
    private Logger logger = LoggerFactory.getLogger(TimsWorldGenerator.class);

    public static int getSpawnChance() {
        return 3;
    }

    public int getSize() {
        return 10;
    }

    @Override public void initialize() {
        stone = CoreRegistry.get(BlockManager.class).getBlock("Core:Stone");
        dirt = CoreRegistry.get(BlockManager.class).getBlock("Core:Dirt");
        water = CoreRegistry.get(BlockManager.class).getBlock("Core:Water");
        air = CoreRegistry.get(BlockManager.class).getBlock("engine:air");

    }

    @Override public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        TempleFacet houseFacet = chunkRegion.getFacet(TempleFacet.class);

        for (Entry<BaseVector3i, Temple> entry : houseFacet.getWorldEntries().entrySet()) {
            Vector3i centerHousePosition = new Vector3i(entry.getKey());
            centerHousePosition.add(entry.getValue().getSize(), entry.getValue().getSize(), entry.getValue().getSize());
            int size = getSize();
            int height = (size + 1) / 2;
            for (int y = 0; y <= height; y++) {
                size--;
                for (int x = 0; x <= size; x++) {
                    for (int z = 0; z <= size; z++) {
                        if ((z == size) || (x == size)) {
                            chunk.setBlock(ChunkMath.calcBlockPos(new Vector3i(x + centerHousePosition.x, y + centerHousePosition.y, z + centerHousePosition.z)), stone);
                        }
                    }
                }
            }

        }
    }

}

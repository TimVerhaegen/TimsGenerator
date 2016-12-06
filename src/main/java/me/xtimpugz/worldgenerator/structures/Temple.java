package me.xtimpugz.worldgenerator.structures;

import me.xtimpugz.worldgenerator.ISpawnableStructure;
import me.xtimpugz.worldgenerator.TimsWorldGenerator;
import me.xtimpugz.worldgenerator.facets.TempleFacet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.math.ChunkMath;
import org.terasology.math.geom.BaseVector3i;
import org.terasology.math.geom.ImmutableVector3i;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;

import java.util.Map;
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
    private boolean shouldPrintBlocks = true;

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
        Map<BaseVector3i, Temple> worldEntries = houseFacet.getWorldEntries();

        for (Entry<BaseVector3i, Temple> entry : worldEntries.entrySet()) {
            ImmutableVector3i startPoint = new ImmutableVector3i(entry.getKey());
            Temple temple = entry.getValue();
            int size = temple.getSize();
            int min = 0;
            int height = (temple.getSize() + 1) / 2;

            for (int i = 0; i <= height; i++) {
                generateLayer(size, min, i, startPoint, chunk);
                min++;
                size--;
            }
        }
    }

    private void generateLayer(int size, int min, int y, ImmutableVector3i centerVector, CoreChunk chunk) {
        chunk.setBlock(ChunkMath.calcBlockPos(new Vector3i(centerVector)), dirt);
        for (int x = min; x <= size; x++) {
            for (int z = min; z <= size; z++) {
                chunk.setBlock(ChunkMath.calcBlockPos(new Vector3i(x, y, z)), stone);
            }
        }
    }

}

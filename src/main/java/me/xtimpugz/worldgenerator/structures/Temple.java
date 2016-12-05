package me.xtimpugz.worldgenerator.structures;

import me.xtimpugz.worldgenerator.ISpawnableStructure;
import me.xtimpugz.worldgenerator.TimsWorldGenerator;
import me.xtimpugz.worldgenerator.facets.TempleFacet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.math.ChunkMath;
import org.terasology.math.Region3i;
import org.terasology.math.geom.BaseVector3i;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;

import java.util.ArrayList;
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
        return 27;
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
            int size = getSize();
            int height = (size + 1) / 2;

            ArrayList<Region3i> toExclude = new ArrayList<>();
            toExclude.add(Region3i.createBounded(new Vector3i(centerHousePosition.add(2, 2, 2)), new Vector3i(centerHousePosition.sub(2, 2, 2))));

            int minCounter = 0;
            int maxCounter = size;

            for (int y = 0; y <= height; y++) {
                generateLayer(minCounter, maxCounter, y, chunk, toExclude);
                minCounter++;
                maxCounter--;
            }

        }
    }

    private void generateLayer(int minCounter, int maxCounter, int y, CoreChunk chunk, ArrayList<Region3i> toExclude) {

        for (int x = minCounter; x <= maxCounter; x++) {
            for (int z = minCounter; z <= maxCounter; z++) {
                chunk.setBlock(ChunkMath.calcBlockPos(x, y, z), stone);
            }
        }
        for (Region3i rg : toExclude) {
            for (Vector3i v : rg) {
                logger.info(v.toString());
                chunk.setBlock(ChunkMath.calcBlockPos(v), stone);
            }
        }

    }

    private boolean shouldPlace(ArrayList<Region3i> toExclude, Vector3i vector3i) {
        return true;
    }

}

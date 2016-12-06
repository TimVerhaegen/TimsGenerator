package me.xtimpugz.worldgenerator.structures;

import me.xtimpugz.worldgenerator.ISpawnableStructure;
import me.xtimpugz.worldgenerator.TimsWorldGenerator;
import me.xtimpugz.worldgenerator.facets.TempleFacet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.math.ChunkMath;
import org.terasology.math.Region3i;
import org.terasology.math.geom.BaseVector3i;
import org.terasology.math.geom.ImmutableVector3i;
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

    public static int getSize() {
        return 28;
    }

    @Override public void initialize() {
        stone = CoreRegistry.get(BlockManager.class).getBlock("Core:Stone");
        dirt = CoreRegistry.get(BlockManager.class).getBlock("Core:Dirt");
        water = CoreRegistry.get(BlockManager.class).getBlock("Core:Water");
        air = CoreRegistry.get(BlockManager.class).getBlock("engine:air");

    }

    @Override public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        TempleFacet templFacet = chunkRegion.getFacet(TempleFacet.class);
        for (Entry<BaseVector3i, Temple> entry : templFacet.getWorldEntries().entrySet()) {
            // there should be a house here
            // create a couple 3d regions to help iterate through the cube shape, inside and out
            Vector3i v = new Vector3i(entry.getKey());
            int size = Temple.getSize();
            int min = 0;
            int height = (Temple.getSize() + 1) / 2;
            ArrayList<Region3i> region3iList = new ArrayList<>();
            Vector3i under = new Vector3i(v).add((size / 2 - 2), 0, 0);
            Vector3i top = new Vector3i(v).add((size / 2 + 2), 2, size);

            region3iList.add(Region3i.createFromMinMax(under, top));

            for (int i = 0; i <= height; i++) {
                generateLayer(size, min, i, new ImmutableVector3i(v), chunk, region3iList);
                min++;
                size--;
            }

        }

    }

    private void generateLayer(int size, int min, int y, ImmutableVector3i centerVector, CoreChunk chunk, ArrayList<Region3i> region3iList) {
        for (int x = min; x <= size; x++) {
            for (int z = min; z <= size; z++) {
                if (shouldRun(region3iList, ChunkMath.calcBlockPos(new Vector3i(x, y, z).add(centerVector))))
                    // THE PROBLEM LAYS HERE!!!! THE BLOCK IS BEING GLITCHED AND FUZZY WUZZY TRANSFORMED.
                    chunk.setBlock(ChunkMath.calcBlockPos(new Vector3i(x, y, z).add(centerVector)), stone);
            }
        }
    }

    private boolean shouldRun(ArrayList<Region3i> region3iList, Vector3i vector) {
        for (Region3i rg : region3iList) {

            if (rg.encompasses(vector)) {
                return false;
            }
        }
        return true;
    }

}

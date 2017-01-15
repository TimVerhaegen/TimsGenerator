package me.xtimpugz.worldgenerator.world.structures;

import me.xtimpugz.worldgenerator.world.facets.LavaPoolFacet;
import org.slf4j.LoggerFactory;
import org.terasology.math.ChunkMath;
import org.terasology.math.geom.BaseVector2i;
import org.terasology.math.geom.BaseVector3i;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizer;

import java.util.Map;

/**
 * Created by jove on 14/01/2017.
 */
public class LavaPoolRasterizer implements WorldRasterizer {
    private Block lava;
    private org.slf4j.Logger logger;

    @Override
    public void initialize() {
        logger = LoggerFactory.getLogger(LavaPoolRasterizer.class);
        lava = CoreRegistry.get(BlockManager.class).getBlock("Core:Lava");
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        LavaPoolFacet facet = chunkRegion.getFacet(LavaPoolFacet.class);
        for(Map.Entry<BaseVector3i, LavaPool> entry :  facet.getWorldEntries().entrySet()){
            Vector3i toSpawnLavaAt = new Vector3i(entry.getKey());
            for(int i = 0; i < 50; i++){
                if(chunk.getRegion().encompasses(toSpawnLavaAt)) chunk.setBlock(ChunkMath.calcBlockPos(toSpawnLavaAt), lava);
                toSpawnLavaAt.sub(0,1,0);

            }
        }
    }
}

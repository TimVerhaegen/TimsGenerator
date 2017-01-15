package me.xtimpugz.worldgenerator.world;

import me.xtimpugz.worldgenerator.world.facets.UndergroundSurfaceFacet;
import org.terasology.math.ChunkMath;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.WorldRasterizer;
import org.terasology.world.generation.facets.SurfaceHeightFacet;

public class TimsWorldRasterizer implements WorldRasterizer {
    private Block dirt;
    private Block stone;
    private Block grass;

    @Override
    public void initialize() {
        grass = CoreRegistry.get(BlockManager.class).getBlock("Core:Grass");
        stone = CoreRegistry.get(BlockManager.class).getBlock("Core:Stone");
        dirt = CoreRegistry.get(BlockManager.class).getBlock("Core:Dirt");
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        SurfaceHeightFacet surfaceHeightFacet = chunkRegion.getFacet(SurfaceHeightFacet.class);
        UndergroundSurfaceFacet underWorldFacet = chunkRegion.getFacet(UndergroundSurfaceFacet.class);
        for (Vector3i position : chunkRegion.getRegion()) {
            int underWorldY = (int) underWorldFacet.getWorld(position.getX(), position.getZ());
            float surfaceHeight = surfaceHeightFacet.getWorld(position.x, position.z);
            int y = position.getY();
            int x = position.getX();
            int z = position.getZ();
            if(y < underWorldY || (y > underWorldY + 47 && y < surfaceHeight-2)){
                chunk.setBlock(ChunkMath.calcBlockPos(x,y,z), stone);
            }else if(y < surfaceHeight && y >= surfaceHeight - 2){
                chunk.setBlock(ChunkMath.calcBlockPos(x,y,z), dirt);
            }else if(y == (int) surfaceHeight){
                chunk.setBlock(ChunkMath.calcBlockPos(x,y,z), grass);
            }
        }
    }
}

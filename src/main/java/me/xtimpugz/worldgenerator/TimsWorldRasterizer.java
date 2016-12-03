package me.xtimpugz.worldgenerator;

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

        for (Vector3i position : chunkRegion.getRegion()) {
            float surfaceHeight = surfaceHeightFacet.getWorld(position.x, position.z);
            if (position.y < surfaceHeight - 5 && position.getY() < TimsWorldGenerator.getDefaultHeight()) {
                chunk.setBlock(ChunkMath.calcBlockPos(position), stone);
            } else if (position.y < surfaceHeight - 2 && position.getY() < TimsWorldGenerator.getDefaultHeight()) {
                chunk.setBlock(ChunkMath.calcBlockPos(position), dirt);
            } else if (position.y < surfaceHeight - 1 && position.getY() <= TimsWorldGenerator.getDefaultHeight()) {
                chunk.setBlock(ChunkMath.calcBlockPos(position), grass);
            } else if (position.getY() > TimsWorldGenerator.getDefaultHeight() && position.getY() <= surfaceHeight) {
                chunk.setBlock(ChunkMath.calcBlockPos(position), stone);
            }
        }
    }
}

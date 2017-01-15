package me.xtimpugz.worldgenerator.world;

import me.xtimpugz.worldgenerator.world.providers.LavaPoolProvider;
import me.xtimpugz.worldgenerator.world.providers.TimsMountainProvider;
import me.xtimpugz.worldgenerator.world.providers.TimsTempleStructureProvider;
import me.xtimpugz.worldgenerator.world.providers.UnderworldSurfaceProvider;
import me.xtimpugz.worldgenerator.world.structures.LavaPoolRasterizer;
import me.xtimpugz.worldgenerator.world.structures.TempleRasterizer;
import org.terasology.core.world.generator.facetProviders.SeaLevelProvider;
import org.terasology.engine.SimpleUri;
import org.terasology.registry.In;
import org.terasology.world.generation.BaseFacetedWorldGenerator;
import org.terasology.world.generation.WorldBuilder;
import org.terasology.world.generator.RegisterWorldGenerator;
import org.terasology.world.generator.plugin.WorldGeneratorPluginLibrary;

@RegisterWorldGenerator(id = "timmysWorld", displayName = "xTimPugz' World")
public class TimsWorldGenerator extends BaseFacetedWorldGenerator {

    @In
    private WorldGeneratorPluginLibrary worldGeneratorPluginLibrary;

    public TimsWorldGenerator(SimpleUri uri) {
        super(uri);
    }

    public static int getDefaultHeight() {
        return 10;
    }

    @Override
    protected WorldBuilder createWorld() {
        return new WorldBuilder(worldGeneratorPluginLibrary)
                .addProvider(new TimsSurfaceProvider())
                .addProvider(new SeaLevelProvider(0))
                .addProvider(new UnderworldSurfaceProvider())
                .addProvider(new LavaPoolProvider())
                .addProvider(new TimsMountainProvider())
                .addProvider(new TimsTempleStructureProvider())
                .addRasterizer(new TimsWorldRasterizer())
                .addRasterizer(new LavaPoolRasterizer())
                .addRasterizer(new TempleRasterizer());
    }
}

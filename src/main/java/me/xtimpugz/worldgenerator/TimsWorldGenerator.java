package me.xtimpugz.worldgenerator;

import me.xtimpugz.worldgenerator.providers.TimsMountainProvider;
import org.terasology.core.world.generator.facetProviders.SeaLevelProvider;
import org.terasology.engine.SimpleUri;
import org.terasology.registry.In;
import org.terasology.world.generation.BaseFacetedWorldGenerator;
import org.terasology.world.generation.WorldBuilder;
import org.terasology.world.generator.RegisterWorldGenerator;
import org.terasology.world.generator.plugin.WorldGeneratorPluginLibrary;

@RegisterWorldGenerator(id = "timsWorld", displayName = "xTimPugz' World")
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
                        .addProvider(new SeaLevelProvider(0))
                        .addProvider(new TimsSurfaceProvider())
                        .addProvider(new TimsMountainProvider())
                        .addRasterizer(new TimsWorldRasterizer());

        }
}

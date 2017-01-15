/*
 * Copyright 2016 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.xtimpugz.worldgenerator.systems;

import me.xtimpugz.worldgenerator.world.TimsSurfaceProvider;
import me.xtimpugz.worldgenerator.world.providers.UnderworldSurfaceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.entitySystem.entity.EntityManager;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.event.ReceiveEvent;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterMode;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.logic.characters.CharacterTeleportEvent;
import org.terasology.logic.common.ActivateEvent;
import org.terasology.logic.inventory.InventoryComponent;
import org.terasology.logic.inventory.InventoryManager;
import org.terasology.logic.inventory.ItemComponent;
import org.terasology.logic.players.LocalPlayer;
import org.terasology.logic.players.PlayerFactory;
import org.terasology.logic.players.event.OnPlayerSpawnedEvent;
import org.terasology.math.ChunkMath;
import org.terasology.math.Region3i;
import org.terasology.math.geom.Vector2i;
import org.terasology.math.geom.Vector3f;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.registry.In;
import org.terasology.world.WorldProvider;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.block.items.BlockItemFactory;
import org.terasology.world.chunks.Chunk;
import org.terasology.world.chunks.ChunkConstants;
import org.terasology.world.chunks.ChunkProvider;

import java.util.Collection;

/**
 * Created by jove on 13/01/2017.
 */

@RegisterSystem(RegisterMode.AUTHORITY)
public class ClickSystem extends BaseComponentSystem {

    private boolean didTp = false;

    @In
    BlockManager blockManager;
    @In
    InventoryManager inventoryManager;
    @In
    EntityManager entityManager;

    @In
    private WorldProvider worldProvider;

    @In
    private LocalPlayer localPlayer;


    private Logger logger;

    @Override
    public void initialise() {
        logger = LoggerFactory.getLogger(ClickSystem.class);
    }

    @ReceiveEvent
    public void place(ActivateEvent event, EntityRef ref) {
        if(event.getTargetLocation() != null) {
            Block b = worldProvider.getBlock(event.getTargetLocation());
            Block teleporter = CoreRegistry.get(BlockManager.class).getBlock("TimmysWorldGen:Teleporter");
            Block air = CoreRegistry.get(BlockManager.class).getBlock("Core:Air");

            Collection<Region3i> relevantRegions = worldProvider.getRelevantRegions();
            int x = (int) localPlayer.getPosition().x();
            int y = (int) localPlayer.getPosition().y();
            int z = (int) localPlayer.getPosition().z();
//            y =  ChunkMath.calcBlockPos(x,y,z).y();

            Region3i reg = null;
            for(Region3i region : relevantRegions){
                if(region.encompasses(x,y,z)){
                    reg = region;
                }
            }

            if (b == teleporter) {

                if(didTp){
                    int yAbovegroundSafe = (int) TimsSurfaceProvider.facetHashMap.get(reg.min()).getWorld(x,z);
                    Vector3i finalToTp = new Vector3i(x, yAbovegroundSafe, z);
                    localPlayer.getCharacterEntity().send(new CharacterTeleportEvent(new Vector3f(finalToTp.x, finalToTp.y, finalToTp.z)));
                    didTp = false;
                    return;
                }
                int yUndergroundSafe = (int) UnderworldSurfaceProvider.facetHashMap.get(reg.min()).getWorld(x,z);

                Vector3i finalToTp = new Vector3i(localPlayer.getPosition().x, yUndergroundSafe, localPlayer.getPosition().z);
                localPlayer.getCharacterEntity().send(new CharacterTeleportEvent(new Vector3f(finalToTp.x, finalToTp.y, finalToTp.z)));
                didTp = true;
            }
        }
    }
}
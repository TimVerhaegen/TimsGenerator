package me.xtimpugz.worldgenerator.world.facets;

import me.xtimpugz.worldgenerator.world.structures.LavaPool;
import me.xtimpugz.worldgenerator.world.structures.Temple;
import org.terasology.math.Region3i;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.facets.base.SparseObjectFacet3D;

/**
 * Created by jove on 14/01/2017.
 */
public class LavaPoolFacet extends SparseObjectFacet3D<LavaPool> {

    public LavaPoolFacet(Region3i targetRegion, Border3D border) {
        super(targetRegion, border);
    }
}

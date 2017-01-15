package me.xtimpugz.worldgenerator.world.facets;

import me.xtimpugz.worldgenerator.world.structures.Temple;
import org.terasology.math.Region3i;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.facets.base.SparseObjectFacet3D;

public class TempleFacet extends SparseObjectFacet3D<Temple> {

    public TempleFacet(Region3i targetRegion, Border3D border) {
        super(targetRegion, border);
    }
}

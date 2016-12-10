package me.xtimpugz.worldgenerator.facets;

import me.xtimpugz.worldgenerator.structures.Temple;
import org.terasology.math.Region3i;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.facets.base.SparseObjectFacet3D;

import java.util.HashMap;
import java.util.Map;

public class TempleFacet extends SparseObjectFacet3D<Temple> {

    Map<TempleFacet, Region3i> facetsRegions = new HashMap<>();

    public TempleFacet(Region3i targetRegion, Border3D border) {
        super(targetRegion, border);
    }
}

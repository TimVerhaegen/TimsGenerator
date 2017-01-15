package me.xtimpugz.worldgenerator.world.facets;

import org.terasology.math.Region3i;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.facets.base.BaseFieldFacet2D;

/**
 * Created by jove on 12/01/2017.
 */
public class UndergroundSurfaceFacet extends BaseFieldFacet2D {

    public UndergroundSurfaceFacet(Region3i targetRegion, Border3D border) {
        super(targetRegion, border);
    }
}

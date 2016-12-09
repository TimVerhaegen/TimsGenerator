package me.xtimpugz.worldgenerator.facets;

import org.terasology.math.Region3i;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.facets.base.SparseBooleanFieldFacet3D;

/**
 * Created by Tim Verhaegen on 3/12/2016.
 */
public class TempleFacet extends SparseBooleanFieldFacet3D {

    public TempleFacet(Region3i targetRegion, Border3D border) {
        super(targetRegion, border);
    }

}

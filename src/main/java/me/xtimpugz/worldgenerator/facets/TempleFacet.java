package me.xtimpugz.worldgenerator.facets;

import me.xtimpugz.worldgenerator.structures.Temple;
import org.terasology.math.Region3i;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.facets.base.SparseObjectFacet3D;

/**
 * Created by Tim Verhaegen on 3/12/2016.
 */
public class TempleFacet extends SparseObjectFacet3D<Temple> {
    public TempleFacet(Region3i region, Border3D border3D) {
        super(region, border3D);
    }

}

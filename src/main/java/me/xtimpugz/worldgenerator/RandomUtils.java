package me.xtimpugz.worldgenerator;

import org.terasology.utilities.random.FastRandom;
import org.terasology.utilities.random.MersenneRandom;
import org.terasology.utilities.random.Random;

/**
 * Created by Tim Verhaegen on 3/12/2016.
 */
public class RandomUtils {
    public static boolean shouldPlace(int chance) {
        MersenneRandom random = new MersenneRandom();
        for (int i = 0; i < chance; i++) {
            int number = random.nextInt(99) + 1;
            if (number == chance) {
                return true;
            }
        }
        return false;
    }

    public static int randomSide() {
        Random random = new FastRandom();
        return random.nextInt(3) + 1;
    }
}

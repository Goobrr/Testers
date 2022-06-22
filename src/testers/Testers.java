package testers;

import arc.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;
import mindustry.type.*;
import testers.ui.*;
import testers.world.blocks.*;

public class Testers extends Mod{
    public Testers(){
        Events.on(FileTreeInitEvent.class, e -> {
            TestersStyles.init();
        });

        Events.on(ClientLoadEvent.class, e -> {
            TestersVars.edit = new EditDialog();
            TestersVars.scripts = Vars.mods.getScripts();

            Log.info("Testers Vars Initialized");//test
        });
    }

    @Override
    public void loadContent(){
        new BaseTester("base-tester"){{
            requirements(Category.logic, ItemStack.with(Items.copper, 1));
            size = 1;
        }};

        new DrawTester("draw-tester"){{
            requirements(Category.logic, ItemStack.with(Items.copper, 1));
            size = 1;
        }};
    }

}

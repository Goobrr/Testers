package testers.world.blocks;

import arc.*;
import arc.func.*;
import arc.util.*;
import mindustry.entities.*;
import mindustry.entities.Effect.*;
import mindustry.gen.*;
import rhino.*;

import static testers.TestersVars.scripts;

public class EffectTester extends BaseTester{
    public EffectTester(String name){
        super(name);
    }

    public class EffectTesterBuild extends TesterBuild{
        public Effect effect;
        public Cons<EffectContainer> cons;

        public float lifetime = 60;

        public void updateCons(String text){
            Core.app.post(() -> {
                try{
                    // oh my fucking god
                    cons = (Cons<EffectContainer>) ((Wrapper) scripts.context.evaluateString(scripts.scope, getCode(text), this + "@" + x + "," + y, 0)).unwrap();
                }catch(Exception e){
                    Log.info(e);
                }
            });
        }

        @Override
        public void run(){
            if(effect == null) effect = new Effect(60, e -> {});
            if(changed){
                updateCons(content);
                effect.renderer = cons;
            }
            if(cons != null){
                effect.lifetime = lifetime;
                try{
                    effect.at(x, y);
                }catch(Exception e){
                    error(e);
                }
            }

            changed = false;
        }
    }
}

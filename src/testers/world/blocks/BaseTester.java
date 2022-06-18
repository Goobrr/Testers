package testers.world.blocks;

import arc.*;
import arc.func.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.world.*;
import rhino.*;
import testers.*;

import static testers.TestersVars.*;

public class BaseTester extends Block{
    public BaseTester(String name){
        super(name);

        update = true;
        solid = true;
        size = 1;

        configurable = true;
        logicConfigurable = false;

        //TODO compression
        config(String.class, (TesterBuild t, String s) -> {
            t.changed = true;
            t.content = s;
        });
    }

    public class TesterBuild extends Building{
        public String content = "";
        public Cons<Building> cons;
        public boolean changed = true;

        @Override
        public void buildConfiguration(Table table){
            table.table(t -> {
                t.button(Icon.pencil, this::edit).size(80);
                t.button(Icon.play, this::run).size(80);
            }).grow();
        }

        public String getCode(String text){
            return "cons(b=>{" + text + "})";
        }

        public void updateCons(String text){
            Core.app.post(() -> {
                try{
                    // oh my fucking god
                    cons = (Cons<Building>) ((Wrapper) scripts.context.evaluateString(scripts.scope, getCode(text), this + "@" + x + "," + y, 0)).unwrap();
                }catch(Exception e){
                    Log.info(e);
                }
            });
        }

        public void run(){
            if(changed) updateCons(content);
            if(cons != null){
                try{
                    cons.get(this);
                }catch(Exception e){
                    error(e);
                }
            }

            changed = false;
        }

        public void error(Exception e){
            Sounds.unlock.play();
            Vars.ui.showLabel("[scarlet]Error:[] " + e.getMessage(), 4, x, y + 8);
        };

        public void edit(){
            edit.show(content, this);
        }

        @Override
        public byte version(){
            return 1;
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);

            if(revision >= 1){
                changed = read.bool();
                content = read.str();

                updateCons(content);
            }
        }

        @Override
        public void write(Writes write){
            super.write(write);

            write.bool(changed);
            write.str(content);
        }
    }
}

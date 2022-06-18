package testers.ui;

import arc.*;
import arc.math.*;
import arc.math.geom.*;
import arc.scene.actions.*;
import arc.scene.event.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.ui.*;

public class LogsTable extends Table{
    public float wx, wy, ox, oy, lx, ly;

    public LogsTable(String text, float duration, float fadetime, float worldx, float worldy){
        wx = worldx;
        wy = worldy;

        margin(4);
        setBackground(Styles.black3);
        touchable = Touchable.disabled;

        actions(Actions.delay(duration), Actions.fadeOut(fadetime), Actions.remove());
        add(text).style(Styles.outlineLabel);

        pack();
        act(0f);
        Core.scene.root.addChildAt(0, this);
        getChildren().first().act(0f);
    };

    // hacky shit
    @Override
    public void act(float delta){
        lx = x;
        ly = y;
        x = ox;
        y = oy;

        super.act(delta);

        ox = x;
        oy = y;
        x = lx;
        y = ly;

        if(Vars.state.isMenu()) remove();
        Vec2 v = Core.camera.project(wx, wy);
        setPosition(v.x + ox, v.y + oy, Align.center);
    }

    public LogsTable addLog(LogsTable t){
        t.parent = this;
        moveUp();
        return t;
    }

    public void moveUp(){
        actions(Actions.parallel(Actions.moveBy(0, getHeight(), 0.1f, Interp.pow3Out)));
        if(parent instanceof LogsTable l) l.moveUp();
    }
}

package testers.ui;

import arc.*;
import arc.scene.ui.*;
import mindustry.gen.*;
import mindustry.ui.dialogs.*;
import testers.world.blocks.BaseTester.*;

public class EditDialog extends BaseDialog{
    public String content = "";
    public TextArea area;

    public TesterBuild build;

    public int lineCount;

    public EditDialog(){
        super(Core.bundle.get("testers.edit"));

        area = cont.add(new TextArea(content.replace("/\r/g", "\n"))).size(1000, Core.graphics.getHeight() - 120).get();
        area.setStyle(TestersStyles.codeField);

        area.setMaxLength(1000); // packet sizes go brrrrr

        buttons.button(Core.bundle.get("ok"), Icon.save, () -> {
            if(build != null) build.configure(area.getText());
            hide();
        });

        buttons.button(Core.bundle.get("cancel"), Icon.exit, this::hide);
    }

    public Dialog show(String content, TesterBuild build){
        this.content = content;
        this.build = build;

        area.setText(this.content);

        return show();
    };
}

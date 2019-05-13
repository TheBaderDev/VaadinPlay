package NotUsed;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;

@Tag("example-template")
@HtmlImport("ExampleTemplate.html")
public class ExampleTemplate extends PolymerTemplate<ExampleModel> {
	
	@EventHandler
	private void handleClick() {
		try {
			Thread.sleep(2000);
			getModel().setValue("Vaadin!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void changeText(String s) {
		getModel().setValue(s);
	}
}

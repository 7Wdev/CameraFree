package org.geyserplus.spigot.sevenwdev.form.elements;

import org.geyserplus.spigot.sevenwdev.form.FormComponentType;
import org.geyserplus.spigot.sevenwdev.form.FormElement;
import org.geysermc.cumulus.component.Component;
import org.geysermc.cumulus.component.InputComponent;

import java.util.function.Consumer;

public class TextInput extends FormElement {
    public String title;
    public String placeholder;
    public String defaultText;

    public Consumer<String> onSubmit;

    public TextInput(String title, Consumer<String> onSubmit) {
        this.title = title;
        this.placeholder = "";
        this.defaultText = "";
        this.onSubmit = onSubmit;
    }

    public TextInput(String title, String placeholder, Consumer<String> onSubmit) {
        this.title = title;
        this.placeholder = placeholder;
        this.defaultText = "";
        this.onSubmit = onSubmit;
    }

    public TextInput(String title, String placeholder, String defaultText, Consumer<String> onSubmit) {
        this.title = title;
        this.placeholder = placeholder;
        this.defaultText = defaultText;
        this.onSubmit = onSubmit;
    }

    @Override
    public FormComponentType getType() {
        return FormComponentType.INPUT;
    }

    @Override
    public void resultRecieved(Object... args) {
        this.onSubmit.accept((String) args[0]);
    }

    @Override
    public Component getComponent() {
        return InputComponent.of(title, placeholder, defaultText);
    }
}
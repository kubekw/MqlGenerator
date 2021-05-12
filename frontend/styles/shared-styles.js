// eagerly import theme styles so as we can override them
import '@vaadin/vaadin-lumo-styles/all-imports';

const $_documentContainer = document.createElement('template');

$_documentContainer.innerHTML = `
<custom-style>
<style include='lumo-badge'>
    
</style>
</custom-style>

<dom-module id="theme-vaadin-button-0" theme-for="vaadin-button">
    <template>
        <style>
        
:host(:not([theme~="tertiary"])) {
  background-image: linear-gradient(var(--lumo-tint-5pct), var(--lumo-shade-5pct));
  box-shadow: inset 0 0 0 1px var(--lumo-contrast-20pct);
}
:host(:not([theme~="tertiary"]):not([theme~="primary"]):not([theme~="error"]):not([theme~="success"])) {
  color: var(--lumo-body-text-color);
}
:host([focus-ring]:not([theme~="tertiary"])) {
  box-shadow: 0 0 0 2px var(--lumo-primary-color-50pct), inset 0 0 0 1px var(--lumo-primary-color);
}
:host([focus-ring][theme~="primary"]) {
  box-shadow: 0 0 0 2px var(--lumo-primary-color-50pct), inset 0 0 0 1px var(--lumo-primary-contrast-color);
}
:host([theme~="primary"]) {
  text-shadow: 0 -1px 0 var(--lumo-shade-20pct);
}
        </style>
    </template>
</dom-module>

`;

document.head.appendChild($_documentContainer.content);

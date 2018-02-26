[![Version](https://img.shields.io/github/release/Invictum/invictum-test-support.svg)](https://github.com/Invictum/invictum-test-support/releases/latest)

Invictum Test Support
=====================

Invictum Test Framework Idea support plugin. Allows to navigate from Panels and Pages locator methods references to related YML markup files.

How to use
-----

Plugin adds `Go To Locator` menu item to the `Navigate` menu section. It allows to navigate from locator keyword usage to its definition in YML file. Menu item is enabled only if following conditions are meet:
 - Idea editor is opened in class that extends `com.github.invictum.panels.AbstractPanel` or `com.github.invictum.pages.AbstractPage` classes
 - Current caret is set to parameters inside of `locate`, `locateAll`, `locator`, `locatorValue` or `data` methods

By default `Go To Locator` item has `Meta + Ctrl + B` shortcut. It doesn't work on Windows, but it is possible to redefine it with Idea IntelliJ configuration.

For now it does not support locator key refactor or intelli sense support.

How to install
--------------

Plugin is not published to Idea IntelliJ public repository, so you need to download jar file in release sections or build it from sources by your own.

Then open Idea settings at `Preferences -> Plugins and locate -> Install Plugin from disc...`. Open plugin jar file and finish installation.

How to build
------------

To build plugin from sources following preconditions should be meet:
 - Installed JDK 8+
 - Installed gradle

Use following steps to build plugin
 1. Clone sources to any folder
 2. Open terminal in directory with sources
 3. Emit following gradle command `gradle clean build`
 4. After successful build plugin jar will be generated at `build/libs` directory
package net.jangaroo.jooc.mvnplugin.sencha;

import net.jangaroo.jooc.mvnplugin.Type;

public interface SenchaConfiguration extends SenchaProfileConfiguration {

  /**
   * Specifies the sencha type generated by the plugin.
   */
  String getType();

  /**
   * The toolkit to use
   *
   * Ignored for {@link Type#WORKSPACE}
   */
  String getToolkit();

  /**
   * Used for {@link Type#APP} and {@link Type#CODE} to define the theme packages to use.
   * For {@link Type#THEME} it defines the theme package to the theme extends.
   *
   * Ignored for {@link Type#WORKSPACE}
   *
   * When a ":" is found in the provided {@link String} it is assumed that a  maven dependency is used as theme and
   * converted properly.
   */
  String getTheme();

  /**
   * Production only configuration
   *
   * Ignored for {@link Type#WORKSPACE}
   */
  SenchaProfileConfiguration getProduction();

  /**
   * Testing only configuration.
   *
   * Ignored for {@link Type#WORKSPACE}
   */
  SenchaProfileConfiguration getTesting();

  /**
   * Development only configuration
   *
   * Ignored for {@link Type#WORKSPACE}
   */
  SenchaProfileConfiguration getDevelopment();

  /**
   * For {@link Type#WORKSPACE} defined the Ext Framework directory for the module.
   *
   * Ignored for {@link Type#APP}, {@link Type#CODE} and {@link Type#THEME}
   */
  String getExtFrameworkDir();

  /**
   * Build directory relative to baseDir
   */
  String getBuildDir();

  /**
   * Packages directory relative to baseDir
   */
  String getPackagesDir();

  /**
   * Skip the build process of the sencha module.
   *
   * Only use this for local development to speed up the build process of the maven app.
   * For deployment the build process is required otherwise remote packages will have no contents.
   *
   * Ignored for {@link Type#WORKSPACE}
   */
  boolean isSkipBuild();

  /**
   * For {@link Type#APP}, {@link Type#CODE} and {@link Type#THEME} specifies if scss paths should map to the maven
   * projects base sencha folder instead of to the generated module.
   *
   * Included paths:
   * - sass.etc
   * - sass.var
   * - sass.src
   *
   * When performaing packaging these paths will be removed temporarily as packages modules may not contain relative
   * paths outside the generated module folder.
   *
   * Ignored for {@link Type#WORKSPACE}
   */
  boolean isScssFromSrc();

  void setToolkit(String toolkit);

  void setTheme(String theme);

  void setExtFrameworkDir(String extFrameworkDir);

  void setPackagesDir(String packagesDir);

  void setScssFromSrc(boolean scssFromSrc);

}
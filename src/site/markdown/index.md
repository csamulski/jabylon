What is Jabylon?
================

Jabylon is an open-source translation server designed to ease the translation of software projects as a team effort.
It provides a web ui for both developers and translators that can be seen as a [live demo](http://demo-jabylon.rhcloud.com/ "Jabylon Demo"). 

![Jabylon](images/jabylon_intro.png) 


## Is Jabylon the right Tool for me?

Currently Jabylon targets primarily developers of java applications that use [property files](http://en.wikipedia.org/wiki/.properties) to translate their software. However, the application is based on [OSGi](http://en.wikipedia.org/wiki/OSGi) and designed to be highly extensible. That means that other translation methods and formats will be supported in the future and you are free to create your own plugins.

Jabylon can be a great help if you are working on a software project that needs to be translated into multiple languages. You can either use as a public server to enable your community to contribute translations to your software or in your intranet to simplify the translation collaboration in your company.


## Key Features

 * powerful web based translation editor
 * automatic translation checks
 * tight integration with source code management systems to synchronize and automatically commit new translations
     * supports multiple branches
     * built-in support for [Git](http://git-scm.com/) and [CVS](http://savannah.nongnu.org/projects/cvs) 
     * plug-in interface for other SCMs
 * full text search
 * fine grained access rights
 * LDAP support
 * JSON API for programmatic access
     
# Deployment

Jabylon runs in an OSGi Container ([Equinox](http://www.eclipse.org/equinox/)) and comes pre-packaged as either a standalone application or as a WAR packaged web application. For details see the [Download Section](./download.html)     

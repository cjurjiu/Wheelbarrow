# Wheelbarrow <img src="https://github.com/cjurjiu/Wheelbarrow/blob/master/media/icons/wheelbarrow.svg" width="60px" /> [ ![Download](https://api.bintray.com/packages/cjurjiu/cjurjiu-opensource/wheelbarrow/images/download.svg) ](https://bintray.com/cjurjiu/cjurjiu-opensource/wheelbarrow/_latestVersion) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://tldrlegal.com/license/apache-license-2.0-(apache-2.0))
Simple Android Library to store objects across config changes. Icon by Freepik @ [Flat Icon](www.flaticon.com).

## About

Wheelbarrow defines two base classes: **WheelbarrowActivity** and **WheelbarrowFragment**. These classes can store an arbitrary object (reffered to as **the cargo**) across Android configuration changes. They also know when they are destroyed & take care of destroying the persistent object as well, to prevent memory leaks.

Technically, they are thin wrappers over the Support Library AppCompatActivity/Fragment classes. The *cargo* is stored using a ViewModel from the Android Architecture Components.

## Use cases

So when is Wheelbarrow useful? Well, it's useful when:
* You want to reuse your Presenter, instead of creating a new one, when your View hierarchy is recreated by a configuration change.
    * In this case, the *cargo* is your Presenter. In the new instance of your Activity/Fragment you would get a reference to your old Presenter. When the Fragment/Activity is destroyed, the Presenter persistent instance will also be destroyed.
* You want to initialise a heavy component just once for a particular screen, without having to use the dreaded Singleton, or a static member.
    * The cargo is only initialised once per Activity/Fragment.
* You might be using Dagger2 or some other dependency injection library, and want to use the same Injector (or Component in Dagger2 lingo) instance to inject the new View, recreated after the configuration change.
    * In this case, the *cargo* is your Injector(Component).
    
## Setup

### With Activities
//TODO

### With Fragments
//TODO


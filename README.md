# Wheelbarrow <img src="https://github.com/cjurjiu/Wheelbarrow/blob/master/media/icons/wheelbarrow.svg" width="60px" /> [ ![Download](https://api.bintray.com/packages/cjurjiu/cjurjiu-opensource/wheelbarrow/images/download.svg) ](https://bintray.com/cjurjiu/cjurjiu-opensource/wheelbarrow/_latestVersion) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://tldrlegal.com/license/apache-license-2.0-(apache-2.0))
Simple Android Library to store objects across config changes. Icon by Freepik @ [Flat Icon](www.flaticon.com).

## Table of Contents
* [About](https://github.com/cjurjiu/Wheelbarrow#about)
* [Use cases](https://github.com/cjurjiu/Wheelbarrow#use-cases)
* [Setup](https://github.com/cjurjiu/Wheelbarrow#setup)
  * [With Activities](https://github.com/cjurjiu/Wheelbarrow#with-activities)
  * [With Fragments](https://github.com/cjurjiu/Wheelbarrow#with-fragments)
  * [Dagger2 Usage](https://github.com/cjurjiu/Wheelbarrow#dagger2-usage)
* [Binaries](https://github.com/cjurjiu/Wheelbarrow#binaries)
* [FAQ](https://github.com/cjurjiu/Wheelbarrow#faq)
  
## About

Wheelbarrow defines two base classes: **WheelbarrowActivity** and **WheelbarrowFragment**. These classes can store an arbitrary object (reffered to as **the cargo**) across Android configuration changes. They also know when they are destroyed & take care of destroying the persistent object as well, to prevent memory leaks.

Technically, they are thin wrappers over the Support Library AppCompatActivity/Fragment classes. The **cargo** is stored using a ViewModel from the Android Architecture Components.

Samples are available:
* [Kotlin Sample](https://github.com/cjurjiu/Wheelbarrow/tree/master/samples/app) 
* [Kotlin Sample - With Dagger2](https://github.com/cjurjiu/Wheelbarrow/tree/master/samples/dagger-app) 

## Use cases

So when is Wheelbarrow useful? Well, it's useful when:
* You want to reuse your Presenter, instead of creating a new one, when your View hierarchy is recreated by a configuration change.
    * In this case, the **cargo** is your Presenter. In the new instance of your Activity/Fragment you would get a reference to your old Presenter. When the Fragment/Activity is destroyed, the Presenter persistent instance will also be destroyed.
* You want to initialise a heavy component just once for a particular screen, without having to use the dreaded Singleton, or a static member.
    * The cargo is only initialised once per Activity/Fragment.
* You might be using Dagger2 or some other dependency injection library, and want to use the same Injector (or Component in Dagger2 lingo) instance to inject the new View, recreated after the configuration change.
    * In this case, the **cargo** is your Injector(Component).
    
## Setup

### With Activities

Assume you're using MVP and you want your Presenter to stay the same across orientation changes. Using Wheelbarrow, this can be achieved in the following way:

```java

//First, extend WheelbarrowActivity, set "CargoType" to your component type. 
//In our case it's "MyPresenter".
public final class MainActivity extends WheelbarrowActivity<MyPresenter> {

    //store the presenter in this Activity instance
    protected MyPresenter presenter;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //remember to call super.onCreate() - not doing so will result in uninitialised cargo 
        super.onCreate(savedInstanceState);
        //Done! Our presenter is now available as Cargo. Get it, save it to our field.
        presenter = (MyPresenter) getCargo();
        
        //do the rest of the initialization. setContentView can also be called before "getCargo()"
        setContentView(R.layout.activity_main);
    }

    @NotNull
    public MyPresenter onCreateCargo() { 
        //onCreateCargo is called only the first time the Activity is created, 
        //when it first creates its Presenter
        //onCreateCargo is invoked during super.onCreate() 
        return MyPresenter(/*configure as required*/);
    }

    @NotNull
    public String getName() { 
        //a name for this Activity is required by Wheelbarrow
        return "MainActivity";
    }
}
```

When your `Activity` instance is created for the first time, `onCreateCargo` will be invoked during `super.onCreate()`. `onCreateCargo` needs an instance of the type to be saved across config changes to be returned (in our case, it's an instance of **MyPresenter**).

After a configuration change, the **cargo** previously set will be reinitialized during `super.onCreate()`. Hence, after `super.onCreate()` the cargo is safe to be read (and maybe assigned to a field of your choosing).

In our sample, this means that an instance of **MyPresenter** is created the first time **MyActivity** is created. After a configuration change, the the **new MainActivity** instance will have access to the previously created **MyPresenter** instance through `getCargo()`. Neat!

### With Fragments

Using the same scenario from the Activity example, configuring your Presenter to be the same across orientation changes is done the following way:

**Declare your Fragment:**

```java

//Extend WheelbarrowFragment, set "CargoType" to your component type. 
//In our case it's "MyPresenter".
public class MainFragment extends WheelbarrowFragment<MyPresenter> {

    public static final String TAG = "Main Fragment";
    //store the presenter in this Fragment instance
    protected MyPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //remember to call super.onCreate() - not doing so will result in uninitialised cargo 
        super.onCreate(savedInstanceState);
        //Done! Our presenter is now available as Cargo. Get it, save it to our field.
        presenter = (MyPresenter) getCargo();
    }

    @Override
    public String getName() {
        //a name for this Fragment is required by Wheelbarrow
        return TAG;
    }
}
```

You might notice there's no `onCreateCargo()` present in the Fragment. This is because subclasses of `WheelbarrowFragment` need to be created by a subclass of `WheelbarrowFragment.Factory`. This allows us to configure the **Cargo** *outside* our Fragment.

Let's go ahead and define our Factory:

```java
//Extend WheelbarrowFragment.Factory, set "CargoType" to your component type. 
//In our case it's "MyPresenter".
public class MainFragmentFactory extends WheelbarrowFragment.Factory<MyPresenter> {

    @Override
    public WheelbarrowFragment<MyPresenter> onCreateFragment() {
        //return a new instance of MainFragment 
        return MainFragment();
    }

    @Override
    public MyPresenter onCreateCargo() {
        //return a new instance of MyPresenter
        return MyPresenter();
    }
}
```
Finally, when adding **MyFragment** on screen, you create it using the **Factory** defined earlier:

```java
  //create a Factory instance
  MainFragmentFactory mainFragmentFactory = new MainFragmentFactory()
  //call "create()" on the Factory to get a usable instance of "MainFragment"
  MainFragment mainFragment = mainFragmentFactory.create()
  //add the Fragment to the Fragment Manager!
  getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, mainFragment, MainFragment.TAG)
                    .commit()
```

And done! Now your **Presenter** will be the same regardless of how many times your **Fragment** gets recreated. Works fine with child Fragments & the Fragment Backstack. Check the samples for how it behaves!

> **Q: That's great but my Fragments & Presenters generally have dependencies. How do I provide those if I use the Factory?**

**A: Add your dependencies to the Factory!** 
Here's an example:

```java
//Extend WheelbarrowFragment.Factory, set "CargoType" to your component type. 
//In our case it's "MyPresenter".
public class MainFragmentFactory extends WheelbarrowFragment.Factory<MyPresenter> {

    //define any parameters/dependencies required by the Fragment or Presenter
    private MyRepository myRepository;
    private boolean presenterParam;
    private int fragmentParam;
   
    //the dependencies are provied to the factory via its constructor, or maybe a Builder pattern.
    public MainFragmentFactory(MyRepository myRepository, 
                               boolean presenterParam, 
                               int fragmentParam) {

        this.myRepository = myRepository;
        this.presenterParam = presenterParam;
        this.fragmentParam = fragmentParam;
    }

    @Override
    public WheelbarrowFragment<MyPresenter> onCreateFragment() {
        //return a new instance of MainFragment
        //provide the Fragment with its dependencies
        return MainFragment.newInstance(fragmentParam);
    }

    @Override
    public MyPresenter onCreateCargo() {
        //return a new instance of MyPresenter
        //Provide the Presenter with its dependencies
        return MyPresenter(myRepository, presenterParam);
    }
}
```

### Dagger2 Usage

When using Dagger2, there might be times when using the same Component (or Subcomponent) after a configuration change is required. 

Such cases arise usually when you want after a config change to inject your Fragments/Activities with the same dependencies used previously, or when you *really* don't want to recreate a module of your component, each time the screen is rotated. 

Of course, those dependencies could be moved to a higher Dagger2 scope (which presumably wouldn't get torn down & recreated each time the screen rotates), but sometimes they might not make sense in the higher scope. 

Since Wheelbarrow persists one object across config changes, it can also persist your Dagger2 Component, for a given Screen (Fragment or Activity)

**Quick setup**

Assume again, you have 2 dependencies injected by Dagger2 that you want to reuse in your **MainActivity** - **MyPresenter**, and **MyRepository**.

Declare your Dagger2 Module & Component and custom scope:

```java
//define custom scope, s.t. the objects created by Dagger2 represent 
//unique instances within the same Dagger2 Component
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface MainActivityScope {}

//define the module
@Module
public class MainActivityModule {    
    //ensure both methods return objects for the "MainActivityScope".

    @Provides
    @MainActivityScope
    public MyPresenter providePresenter(...) {
        return new MyPresenter(...)
    }
    
    @Provides
    @MainActivityScope
    public MyRepository provideRepository(...) {
        return new MyRepository(...)
    }
}

//define the Component
@MainActivityScope
@Component(modules = { MainActivityModule.class })
public abstract class MainActivityComponent {

    abstract void inject(MainActivity mainActivity)
}
```

Now, in **MainActivity.java**:

```java
//Extend WheelbarrowActivity, set "CargoType" to your Dagger2 Component type. 
//In our case it's "MainActivityComponent".
public final class MainActivity extends WheelbarrowActivity<MainActivityComponent> {

    //fields to store the Presenter & Repository to be injected
    @Inject
    protected MyPresenter presenter;
    @Inject
    protected MyRepository repository;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //remember to call super.onCreate() - not doing so will result in uninitialised cargo 
        super.onCreate(savedInstanceState);
        
        //Done! Our Dagger2 component (of type MainActivityComponent) is now available as Cargo.
        //Get it, and inject our Activity
        getCargo().inject(this);
        
        //do the rest of the initialization. setContentView can also be called before "getCargo()"
        setContentView(R.layout.activity_main);
    }

    @NotNull
    public MainActivityComponent onCreateCargo() { 
        //onCreateCargo is called only the first time the Activity is created, 
        //when it first creates its Dagger2 Component
        //onCreateCargo is invoked during super.onCreate() 
        return new MainActivityComponent(new MainActivityModule(...));
    }

    @NotNull
    public String getName() { 
        //a name for this Activity is required by Wheelbarrow
        return "MainActivity";
    }
}
```

And you're done! After each configuration change, in `onCreate(...)` you only need to call `getCargo().inject(this)` to make sure your `Activity` is Injected with the same dependencies as before. 

For `Fragments` the process is similar. The main difference is that you create the Dagger2 Component in your `WheelbarrowFragment.Factory` subclass, and not in the `Fragment` itself.

See also the [Dagger Sample](https://github.com/cjurjiu/Wheelbarrow/tree/master/samples/dagger-app) for reference.

## Binaries

Binaries and dependency information for Maven, Ivy, Gradle and others can be found on [jcenter](https://bintray.com/cjurjiu/cjurjiu-opensource/wheelbarrow).

Example for Gradle:

```groovy
implementation 'com.catalinjurjiu:wheelbarrow:0.0.2'
```

and for Maven:

```xml
<dependency>
  <groupId>com.catalinjurjiu</groupId>
  <artifactId>wheelbarrow</artifactId>
  <version>0.0.2</version>
  <type>pom</type>
</dependency>
```
and for Ivy:

```xml
<dependency org='com.catalinjurjiu' name='wheelbarrow' rev='0.0.2'>
  <artifact name='wheelbarrow' ext='pom' ></artifact>
</dependency>
```

## FAQ
//TODO

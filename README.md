# Wheelbarrow <img src="https://github.com/cjurjiu/Wheelbarrow/blob/master/media/icons/wheelbarrow.svg" width="60px" /> [ ![Download](https://api.bintray.com/packages/cjurjiu/cjurjiu-opensource/wheelbarrow/images/download.svg) ](https://bintray.com/cjurjiu/cjurjiu-opensource/wheelbarrow/_latestVersion) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://tldrlegal.com/license/apache-license-2.0-(apache-2.0))
Simple Android Library to store objects across config changes. Icon by Freepik @ [Flat Icon](www.flaticon.com).

## About

Wheelbarrow defines two base classes: **WheelbarrowActivity** and **WheelbarrowFragment**. These classes can store an arbitrary object (reffered to as **the cargo**) across Android configuration changes. They also know when they are destroyed & take care of destroying the persistent object as well, to prevent memory leaks.

Technically, they are thin wrappers over the Support Library AppCompatActivity/Fragment classes. The **cargo** is stored using a ViewModel from the Android Architecture Components.

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
        //Done! Our presenter is not available as Cargo. Get it, save it to the field.
        presenter = ((MyPresenter) getCargo());
        
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
//TODO

### With Dagger2
//TODO

### Binaries
//TODO

### FAQ
//TODO

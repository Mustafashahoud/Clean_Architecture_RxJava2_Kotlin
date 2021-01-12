# CleanArchitecture_RxJava2_Kotlin

* Simple Android app shows a bitcoin price chart from [Blockchain Api](https://www.blockchain.com/api)
* MVVM + Clean Architecture.
* A single-activity pattern, using the Navigation component to manage fragment operations.
* Single Source of Truth.

## Libraries
- 100% Kotlin
- MVVM + Clean Architecture
- Architecture Components (Lifecycle, LiveData, ViewModel, DataBinding, Navigation, Room)
- [Blockchain](https://www.blockchain.com/api)
- [Dagger2](https://github.com/google/dagger) for dependency injection
- [Retrofit2 & Gson](https://github.com/square/retrofit) for REST API
- [RxJava](https://github.com/ReactiveX/RxJava/tree/2.x)
- [LeakCanary](https://square.github.io/leakcanary/) for detecting memory leak
- [Mockito-kotlin](https://github.com/nhaarman/mockito-kotlin) for Junit mock test
- [Espresso](https://developer.android.com/training/testing/espresso) for UI testing
- [Timber](https://github.com/JakeWharton/timber) for logging

## Screenshots
<p align="center">
<img src="/art/bitcoinCharts_screenshot.jpg" width="40%"/>
</p>

## Notes

- The main goal behind me choosing to modularize this app by layers (data, domain, presentation) is to show you a clear example about MVVM + clean architecture + Single Activity Pattern + Single Source of truth. But I think for real apps modularizing by features is much better.
- For caching I have used SQLite Room.
- For Error handling I have used a GeneralErrorHandlerImpl class which is sited in the data layer and it is an implementation of the interface ErrorHandler that is sited in the domain layer that would be injected in the viewModel and then deciding what kind of Error we have. If we have a network error I show a retry and if it is Server Error a GoBack button. Many other mechanisms could have been used such as letting the interactor/UseCase return either ERROR or SUCCESSS.
- I have used Databinding that works well when wrapping the result in the viewModel with three types Loading, Success, Error with Livedata and then UI controller observes it and reacts according to the type.
- I have used Single Activity pattern with a fragment for each screen/feature and Navigation component for navigating between fragments/features.
- For Dependency Injection I have created three scopes PerApplication, PerActivity (Which is unnecessary for Single Activity Pattern) and PerFeature (Per Fragment).
- I have used the plugin allOpen to make the classes that need an AndroidTest open by using OpenClass for debug variant and without it for Release variant, so classes in the Release would final.
- For UI test I have used Espressos with Fragment Scenario which is good to test a fragment in separation without having to add it to a TestActivity.
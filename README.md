# RxLinkClawler
Android library for grabbing info from link using reactive way

<img src="https://raw.githubusercontent.com/afeozzz/RxLinkClawler/master/images/1.jpg" height="420">

## How to use:
### Gradle:

Include this code in your app gradle

```
dependencies {    
        compile 'io.afeozzz.android:rxlinkcrawler:1.0.2'
 }
```
    
### Implementation

```java
 Subscription subscription = RxLinkClawler.grab(okHttpClient, url))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<LinkPreview>() {
                    @Override
                    public void call(LinkPreview clawlerPreview) {
                        if (clawlerPreview != null) {
                           //handle clawler preview
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
```

## Specially:

Thanks https://github.com/LeonardoCardoso/Android-Link-Preview for some ideas and regexp code

## Inner libs:
[jsoup](https://jsoup.org/)
[okhttp](https://github.com/square/okhttp)
[rxjava](https://github.com/ReactiveX/RxJava)
[rxandroid](https://github.com/ReactiveX/RxAndroid)

License
-------

    Copyright 2016 Ilnar Karimov

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

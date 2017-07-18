package com.giaquino.sample.common.extensions

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.asyncSubscribeOnMain(next: (T) -> Unit, error: (Throwable) -> Unit)
    = subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe({ t -> next.invoke(t) }, { throwable -> error.invoke(throwable) })

fun <T> Observable<T>.asyncSubscribeOnMain(next: (T) -> Unit)
    = subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe({ t -> next.invoke(t) }, { _ -> })

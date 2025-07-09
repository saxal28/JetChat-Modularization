package com.example.compose.jetchat.core.data.datasource;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class FakeDataSource_Factory implements Factory<FakeDataSource> {
  @Override
  public FakeDataSource get() {
    return newInstance();
  }

  public static FakeDataSource_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FakeDataSource newInstance() {
    return new FakeDataSource();
  }

  private static final class InstanceHolder {
    static final FakeDataSource_Factory INSTANCE = new FakeDataSource_Factory();
  }
}

package com.example.compose.jetchat.core.data.repository.impl;

import com.example.compose.jetchat.core.data.datasource.FakeDataSource;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
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
public final class FakeUserRepository_Factory implements Factory<FakeUserRepository> {
  private final Provider<FakeDataSource> dataSourceProvider;

  public FakeUserRepository_Factory(Provider<FakeDataSource> dataSourceProvider) {
    this.dataSourceProvider = dataSourceProvider;
  }

  @Override
  public FakeUserRepository get() {
    return newInstance(dataSourceProvider.get());
  }

  public static FakeUserRepository_Factory create(
      javax.inject.Provider<FakeDataSource> dataSourceProvider) {
    return new FakeUserRepository_Factory(Providers.asDaggerProvider(dataSourceProvider));
  }

  public static FakeUserRepository_Factory create(Provider<FakeDataSource> dataSourceProvider) {
    return new FakeUserRepository_Factory(dataSourceProvider);
  }

  public static FakeUserRepository newInstance(FakeDataSource dataSource) {
    return new FakeUserRepository(dataSource);
  }
}

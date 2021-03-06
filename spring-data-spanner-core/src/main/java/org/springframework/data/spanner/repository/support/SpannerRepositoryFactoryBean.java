/*
 * Copyright 2017 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.data.spanner.repository.support;

import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.spanner.core.SpannerOperations;
import org.springframework.data.spanner.core.mapping.SpannerMappingContext;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * Created by rayt on 3/23/17.
 */
public class SpannerRepositoryFactoryBean<T extends Repository<S, ID>, S, ID extends Serializable>
    extends RepositoryFactoryBeanSupport<T, S, ID> {

  private SpannerOperations operations;
  private SpannerMappingContext mappingContext;

  /**
   * Creates a new {@link SpannerRepositoryFactoryBean} for the given repository interface.
   *
   * @param repositoryInterface must not be {@literal null}.
   */
  public SpannerRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
    super(repositoryInterface);
  }

  public void setSpannerOperations(SpannerOperations operations) {
    this.operations = operations;
  }

  public void setMappingContext(SpannerMappingContext mappingContext) {
    super.setMappingContext(mappingContext);
    this.mappingContext = mappingContext;
  }

  @Override
  protected RepositoryFactorySupport createRepositoryFactory() {
    return new SpannerRepositoryFactory(operations);
  }

  @Override
  public void afterPropertiesSet() {

    super.afterPropertiesSet();
    Assert.notNull(operations, "SpannerTemplate must not be null!");

    if (mappingContext == null) {
      setMappingContext(operations.getMappingContext());
    }
  }
}

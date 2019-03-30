/*
 * Copyright 2015-present Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.facebook.buck.query;

import com.facebook.buck.core.model.BuildTarget;
import com.google.common.collect.ImmutableSet;
import javax.annotation.Nullable;

/** Implementation of {@link QueryTarget} that wraps a {@link BuildTarget}. */
public final class QueryBuildTarget implements QueryTarget {

  private final BuildTarget buildTarget;

  private QueryBuildTarget(BuildTarget buildTarget) {
    this.buildTarget = buildTarget;
  }

  /**
   * Construct a new immutable {@code QueryBuildTarget} instance.
   *
   * @param buildTarget The value for the {@code buildTarget} attribute
   * @return An immutable QueryBuildTarget instance
   */
  public static QueryBuildTarget of(BuildTarget buildTarget) {
    return new QueryBuildTarget(buildTarget);
  }

  /** @return target as {@link QueryBuildTarget} or throw {@link IllegalArgumentException} */
  public static QueryBuildTarget asQueryBuildTarget(QueryTarget target) {
    if (!(target instanceof QueryBuildTarget)) {
      throw new IllegalArgumentException(
          String.format(
              "Expected %s to be a build target but it was an instance of %s",
              target, target.getClass().getName()));
    }

    return (QueryBuildTarget) target;
  }

  /** @return set as {@link QueryBuildTarget}s or throw {@link IllegalArgumentException} */
  @SuppressWarnings("unchecked")
  public static ImmutableSet<QueryBuildTarget> asQueryBuildTargets(
      ImmutableSet<? extends QueryTarget> set) {
    // It is probably rare that there is a QueryTarget that is not a QueryBuildTarget.
    boolean hasInvalidItem = set.stream().anyMatch(item -> !(item instanceof QueryBuildTarget));
    if (!hasInvalidItem) {
      return (ImmutableSet<QueryBuildTarget>) set;
    } else {
      throw new IllegalArgumentException(
          String.format("%s has elements that are not QueryBuildTarget", set));
    }
  }

  /** @return the set filtered by items that are instances of {@link QueryBuildTarget} */
  @SuppressWarnings("unchecked")
  public static ImmutableSet<QueryBuildTarget> filterQueryBuildTargets(
      ImmutableSet<? extends QueryTarget> set) {
    // It is probably rare that there is a QueryTarget that is not a QueryBuildTarget.
    boolean needsFilter = set.stream().anyMatch(item -> !(item instanceof QueryBuildTarget));
    if (!needsFilter) {
      return (ImmutableSet<QueryBuildTarget>) set;
    } else {
      return set.stream()
          .filter(QueryBuildTarget.class::isInstance)
          .map(QueryBuildTarget.class::cast)
          .collect(ImmutableSet.toImmutableSet());
    }
  }

  /** @return The value of the {@code buildTarget} attribute */
  public BuildTarget getBuildTarget() {
    return buildTarget;
  }

  @Override
  public String toString() {
    return buildTarget.toString();
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (!(other instanceof QueryBuildTarget)) {
      return false;
    }

    QueryBuildTarget that = (QueryBuildTarget) other;
    return buildTarget.equals((that.buildTarget));
  }

  @Override
  public int hashCode() {
    return buildTarget.hashCode();
  }
}

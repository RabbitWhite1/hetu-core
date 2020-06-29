/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.prestosql.server;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import io.airlift.configuration.Config;
import io.airlift.resolver.ArtifactResolver;

import javax.validation.constraints.NotNull;

import java.io.File;
import java.util.List;

public class PluginManagerConfig
{
    private File installedPluginsDir = new File("plugin");
    private File externalFunctionsPluginsDir = new File("externalFunctionsPlugin");
    private File externalFunctionsDir = new File("externalFunctions");
    private long maxFunctionRunningTimeInSec = 600;
    private List<String> plugins;
    private String mavenLocalRepository = ArtifactResolver.USER_LOCAL_REPO;
    private List<String> mavenRemoteRepository = ImmutableList.of(ArtifactResolver.MAVEN_CENTRAL_URI);

    public File getInstalledPluginsDir()
    {
        return installedPluginsDir;
    }

    @Config("plugin.dir")
    public PluginManagerConfig setInstalledPluginsDir(File installedPluginsDir)
    {
        this.installedPluginsDir = installedPluginsDir;
        return this;
    }

    public File getExternalFunctionsPluginsDir()
    {
        return this.externalFunctionsPluginsDir;
    }

    @Config("external-functions-plugin.dir")
    public PluginManagerConfig setExternalFunctionsPluginsDir(File dir)
    {
        this.externalFunctionsPluginsDir = dir;
        return this;
    }

    public File getExternalFunctionsDir()
    {
        return this.externalFunctionsDir;
    }

    @Config("external-functions.dir")
    public PluginManagerConfig setExternalFunctionsDir(File externalFunctionsDir)
    {
        this.externalFunctionsDir = externalFunctionsDir;
        return this;
    }

    public long getMaxFunctionRunningTimeInSec()
    {
        return this.maxFunctionRunningTimeInSec;
    }

    @Config("max-function-running-time-in-second")
    public PluginManagerConfig setMaxFunctionRunningTimeInSec(long time)
    {
        this.maxFunctionRunningTimeInSec = time;
        return this;
    }

    public List<String> getPlugins()
    {
        return plugins;
    }

    public PluginManagerConfig setPlugins(List<String> plugins)
    {
        this.plugins = plugins;
        return this;
    }

    @Config("plugin.bundles")
    public PluginManagerConfig setPlugins(String plugins)
    {
        if (plugins == null) {
            this.plugins = null;
        }
        else {
            this.plugins = ImmutableList.copyOf(Splitter.on(',').omitEmptyStrings().trimResults().split(plugins));
        }
        return this;
    }

    @NotNull
    public String getMavenLocalRepository()
    {
        return mavenLocalRepository;
    }

    @Config("maven.repo.local")
    public PluginManagerConfig setMavenLocalRepository(String mavenLocalRepository)
    {
        this.mavenLocalRepository = mavenLocalRepository;
        return this;
    }

    @NotNull
    public List<String> getMavenRemoteRepository()
    {
        return mavenRemoteRepository;
    }

    public PluginManagerConfig setMavenRemoteRepository(List<String> mavenRemoteRepository)
    {
        this.mavenRemoteRepository = mavenRemoteRepository;
        return this;
    }

    @Config("maven.repo.remote")
    public PluginManagerConfig setMavenRemoteRepository(String mavenRemoteRepository)
    {
        this.mavenRemoteRepository = ImmutableList.copyOf(Splitter.on(',').omitEmptyStrings().trimResults().split(mavenRemoteRepository));
        return this;
    }
}

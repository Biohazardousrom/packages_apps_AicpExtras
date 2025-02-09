/*
 * Copyright (C) 2017 AICP
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.aicp.extras.fragments;

import android.os.Bundle;
import android.os.Handler;
import androidx.preference.Preference;

import java.net.InetAddress;

import com.aicp.extras.BaseSettingsFragment;
import com.aicp.extras.R;
import com.aicp.extras.utils.Util;

 public class SystemExtensions extends BaseSettingsFragment
             implements Preference.OnPreferenceChangeListener {

//    private static final String PREF_SYSTEM_APP_REMOVER = "system_app_remover";
    private static final String PREF_ADBLOCK = "persist.aicp.hosts_block";

    private Handler mHandler = new Handler();

    @Override
    protected int getPreferenceResource() {
        return R.xml.system_extensions;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*        Preference systemAppRemover = findPreference(PREF_SYSTEM_APP_REMOVER);
        Util.requireRoot(getActivity(), systemAppRemover);
*/
        findPreference(PREF_ADBLOCK).setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (PREF_ADBLOCK.equals(preference.getKey())) {
            // Flush the java VM DNS cache to re-read the hosts file.
            // Delay to ensure the value is persisted before we refresh
            mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        InetAddress.clearDnsCache();
                    }
            }, 1000);
            return true;
        } else {
            return false;
        }
    }
}

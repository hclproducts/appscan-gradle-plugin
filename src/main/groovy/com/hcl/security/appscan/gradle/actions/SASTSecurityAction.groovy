/**
 * @ Copyright HCL Technologies Ltd. 2018, 2019.
 * LICENSE: Apache License, Version 2.0 https://www.apache.org/licenses/LICENSE-2.0
 */

package com.hcl.security.appscan.gradle.actions

import com.hcl.appscan.sdk.CoreConstants
import com.hcl.appscan.sdk.scan.IScanManager
import com.hcl.appscan.sdk.scan.ITarget
import com.hcl.appscan.sdk.scanners.sast.SASTScanManager
import org.gradle.api.Project

abstract class SASTSecurityAction extends SecurityAction {

    public SASTSecurityAction(Project project, Collection<ITarget> targets) {
        super(project, targets);
        File irx = new File(project.appscanSettings.irxDir, project.appscanSettings.irxName + ".irx"); //$NON-NLS-1$
        if(irx.isFile())
            irx.delete();
    }

    @Override
    protected Map<String, String> getOptions() {
        Map<String, String> options = new HashMap<String, String>()
        options.put(CoreConstants.SCAN_NAME, getProject().appscanSettings.irxName)
        options.put(CoreConstants.APP_ID, getProject().appscanSettings.appId);
        options.put("APPSCAN_IRGEN_CLIENT", "Gradle");
        return options;
    }

    @Override
    protected IScanManager initScanManager() {
        IScanManager manager = new SASTScanManager(getProject().appscanSettings.irxDir);
        for(ITarget target : getTargets())
            manager.addScanTarget(target);
        return manager;
    }
}

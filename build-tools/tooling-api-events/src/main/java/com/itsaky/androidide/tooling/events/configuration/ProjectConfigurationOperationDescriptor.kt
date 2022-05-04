/*
 *  This file is part of AndroidIDE.
 *
 *  AndroidIDE is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  AndroidIDE is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with AndroidIDE.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.itsaky.androidide.tooling.events.configuration

import com.itsaky.androidide.tooling.model.ProjectIdentifier
import com.itsaky.androidide.tooling.events.OperationDescriptor

/** @author Akash Yadav */
class ProjectConfigurationOperationDescriptor(
    val project: ProjectIdentifier,
    override val name: String,
    override val displayName: String,
    override val parent: OperationDescriptor?
) : OperationDescriptor() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ProjectConfigurationOperationDescriptor) return false
        
        if (project != other.project) return false
        if (name != other.name) return false
        if (displayName != other.displayName) return false
        if (parent != other.parent) return false
        
        return true
    }
    
    override fun hashCode(): Int {
        var result = project.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + displayName.hashCode()
        result = 31 * result + (parent?.hashCode() ?: 0)
        return result
    }
}
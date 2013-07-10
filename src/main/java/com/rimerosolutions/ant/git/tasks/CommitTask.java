/*
 * Copyright 2013 Rimero Solutions
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
package com.rimerosolutions.ant.git.tasks;

import org.apache.tools.ant.BuildException;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import com.rimerosolutions.ant.git.GitBuildException;

/**
 * Commits all local changes
 * 
 * @author Yves Zoundi
 */
public class CommitTask extends AbstractGitRepoAwareTask {

        private String message = "Commit message";
        private String username;
        private String email;
        private boolean all = true;
        private boolean amend = false;
        private String reflogComment;



        @Override
        protected void doExecute() throws BuildException {
                try {
                        setFailOnError(true);
                        CommitCommand cmd = Git.wrap(repo).commit();
                        if (username != null && email != null) {
                               cmd.setAuthor(username, email);
                        }
                        
                        if (message != null) {
                                cmd.setMessage(message);
                        }
                        
                        cmd.setAll(all).setAmend(amend);
                        
                        if (reflogComment != null) {
                                cmd.setReflogComment(reflogComment);
                        }
                        
                        cmd.call();
                } catch (GitAPIException ex) {
                        throw new GitBuildException("Cannot commit to Git repository", ex);
                }
        }
        
        /**
         * @param username The name of the author used for the commit
         */
        public void setUsername(String username) {
                this.username = username;
        }

        /**
         * @param email The email of the author used for the commit
         */
        public void setEmail(String email) {
                this.email = email;
        }

        /**
         * @param all
         *                the all to set
         */
        public void setAll(boolean all) {
                this.all = all;
        }

        /**
         * @param amend
         *                the amend to set
         */
        public void setAmend(boolean amend) {
                this.amend = amend;
        }

        /**
         * @param reflogComment
         *                the reflogComment to set
         */
        public void setReflogComment(String reflogComment) {
                this.reflogComment = reflogComment;
        }

        public void setMessage(String message) {
                this.message = message;
        }

}
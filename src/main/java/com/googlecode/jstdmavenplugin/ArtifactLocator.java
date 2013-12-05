package com.googlecode.jstdmavenplugin;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionRequest;
import org.apache.maven.artifact.resolver.ArtifactResolver;

public class ArtifactLocator {
	private final ArtifactFactory artifactFactory;
	private final ArtifactResolver artifactResolver;
	private final ArtifactRepository localRepository;

	public ArtifactLocator(ArtifactFactory artifactFactory, ArtifactResolver artifactResolver, ArtifactRepository localRepository) {
		this.artifactFactory = artifactFactory;
		this.artifactResolver = artifactResolver;
		this.localRepository = localRepository;
	}

	public String getAbsolutePathToArtifact(final MavenCoordinate coordinate) throws ArtifactNotFoundException {
		Artifact artifact = artifactFactory.createArtifact(coordinate.getGroupId(), coordinate.getArtifactId(), coordinate.getVersion(), "", coordinate.getFileType());
		ArtifactResolutionRequest request = new ArtifactResolutionRequest();
		request.setArtifact(artifact);
		request.setLocalRepository(localRepository);
		artifactResolver.resolve(request);
		if (artifact.getFile() == null) {
			throw new ArtifactNotFoundException("Unable to find artifact.", artifact);
		}
		return artifact.getFile().getAbsolutePath();
	}

}

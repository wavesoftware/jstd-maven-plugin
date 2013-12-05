package com.googlecode.jstdmavenplugin;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.fail;
import static org.testng.AssertJUnit.assertSame;

/**
 * Copyright 2009-2011, Burke Webster (burke.webster@gmail.com)
 */
@Test
public class ArtifactLocatorTest
{

	private static final String A_PATH = System.getProperty("user.home");

	private Artifact goodArtifact;
	private Artifact badArtifact;
	private ArtifactFactory artifactFactory;
    private ArtifactLocator locator;
	private MavenCoordinate coord;

    @BeforeMethod
    public void setUp()
    {
		ArtifactResolver artifactResolver = mock(ArtifactResolver.class);
		goodArtifact = mock(Artifact.class);
		when(goodArtifact.getFile()).thenReturn(new File(A_PATH));
		badArtifact = mock(Artifact.class);
		when(badArtifact.getFile()).thenReturn(null);

		coord = mock(MavenCoordinate.class);
		when(coord.getGroupId()).thenReturn("com.google.jstestdriver");
		when(coord.getArtifactId()).thenReturn("jstestdriver");
		when(coord.getFileType()).thenReturn("jar");
		when(coord.getVersion()).thenReturn("1.3.5");
		artifactFactory = mock(ArtifactFactory.class);

        locator = new ArtifactLocator(artifactFactory, artifactResolver, null);
    }

    public void shouldFindArtifact()
    {
		when(artifactFactory.createArtifact(coord.getGroupId(), coord.getArtifactId(), coord.getVersion(), "", coord.getFileType())).thenReturn(goodArtifact);
		try {
        	assertSame(locator.getAbsolutePathToArtifact(coord), A_PATH);
		} catch (Exception e) {
			fail("Should not have thrown an exception.");
		}
    }

    public void shouldThrowExceptionForInvalidGroupId()
    {
		String badGroupId = "com.google.jstestdriver.some.invalid.group";
		when(artifactFactory.createArtifact(badGroupId, coord.getArtifactId(), coord.getVersion(), "", coord.getFileType())).thenReturn(badArtifact);
		when(coord.getGroupId()).thenReturn(badGroupId);
        try
        {
            locator.getAbsolutePathToArtifact(coord);
            fail("Should have thrown an exception for invalid groupId");
        }
        catch (ArtifactNotFoundException ignored) {}
    }

    public void shouldThrowExceptionForInvalidArtifactId()
    {
		String badArtifactId = "jstestdriver-invalid-artifact";
		when(artifactFactory.createArtifact(coord.getGroupId(), badArtifactId, coord.getVersion(), "", coord.getFileType())).thenReturn(badArtifact);
		when(coord.getArtifactId()).thenReturn(badArtifactId);
        try
        {
			locator.getAbsolutePathToArtifact(coord);
            fail("Should have thrown an exception for invalid artifactId");
        }
		catch (ArtifactNotFoundException ignored) {}
    }

	public void shouldThrowExceptionForInvalidVersion()
	{
		String badVersion = "0";
		when(artifactFactory.createArtifact(coord.getGroupId(), coord.getArtifactId(), badVersion, "", coord.getFileType())).thenReturn(badArtifact);
		when(coord.getVersion()).thenReturn(badVersion);
		try
		{
			locator.getAbsolutePathToArtifact(coord);
			fail("Should have thrown an exception for invalid version");
		}
		catch (ArtifactNotFoundException ignored) {}
	}

	public void shouldThrowExceptionForInvalidFileType()
	{
		String badFileType = "jstestdriver-invalid-artifact";
		when(artifactFactory.createArtifact(coord.getGroupId(), coord.getArtifactId(), coord.getVersion(), "", badFileType)).thenReturn(badArtifact);
		when(coord.getFileType()).thenReturn(badFileType);
		try
		{
			locator.getAbsolutePathToArtifact(coord);
			fail("Should have thrown an exception for invalid artifactId");
		}
		catch (ArtifactNotFoundException ignored) {}
	}

}
